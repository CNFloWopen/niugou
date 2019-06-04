package com.CNFloWopen.niugou.service.impl;


import com.CNFloWopen.niugou.dao.ProductDao;
import com.CNFloWopen.niugou.dao.ProductImgDao;
import com.CNFloWopen.niugou.dto.ImageHolder;
import com.CNFloWopen.niugou.dto.ProductExecution;
import com.CNFloWopen.niugou.entity.Product;
import com.CNFloWopen.niugou.entity.ProductImg;
import com.CNFloWopen.niugou.enums.ProductStateEnum;
import com.CNFloWopen.niugou.exceptions.ProductOperationException;
import com.CNFloWopen.niugou.service.ProductService;
import com.CNFloWopen.niugou.util.ImageUtil;
import com.CNFloWopen.niugou.util.PageCalculatop;
import com.CNFloWopen.niugou.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;
    @Override
    @Transactional
    //1.处理缩略图，获取缩略图的相对路径并赋值给product
    //2.往tb_product中写入商品信息,并录入productId
    //3.结合productId批量处理商品详情图
    //4.将商品详情图列表批量插入tb_product_img中
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException {
        //空值判断
        if (product !=null && product.getShop() != null && product.getShop().getShopId()!=null)
        {
//            给商品设置上默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
//            默认为上架状态
            product.setEnableStatus(1);
//            若商品的缩略图不为空则添加
            if (thumbnail != null)
            {
                addThumbnail(product,thumbnail);
            }
            try {
                //创建商品信息
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum<=0)
                {
                    throw new ProductOperationException("创建商品失败");
                }
            }catch (Exception e)
            {
                throw new ProductOperationException("创建失败"+e.getMessage());
            }
            //若商品详情图不为空则添加
            if (productImgHolderList != null && productImgHolderList.size() >0)
            {
                addProductImgList(product,productImgHolderList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS,product);

        }else {
            //传值错误或出现参数错误
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    /**
     * 分页商品列表
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        //页码转化为数据库的行码，并调用dao层取回指定的页码的商品列表
        int rowIndex = PageCalculatop.calculateRowIndex(pageIndex,pageSize);
        List<Product> productList = productDao.queryProductList(productCondition,rowIndex,pageSize);
        //基于同样的查询条件返回该查询条件下的商品总数
        int count = productDao.queryProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }
    //获取商品ID
    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }
    //1。若缩略图参数有值，则处理缩略图
    //若原先存在缩略图则先删除再添加，之后获取缩略图相对路径并赋值给Product
    //2。若商品详情图列表参数有值，对商品详情图进行同样的操作
    //3。将tb_product_img下面的该商品原先的商品详情图片记录全部删除
    //4。更新tb_product的信息
    @Override
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productHolderList) throws ProductOperationException {
        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            //给商品设置上默认属性
            product.setLastEditTime(new Date());
            //若商品的缩略图不为空且原有的缩略图不为空则添加
            if (thumbnail != null) {
                //先获取一遍原有信息，因为原有的信息里有图片地址
                Product tempProduct = productDao.queryProductById(product.getProductId());
                if (tempProduct.getImgAddr() != null) {
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(product, thumbnail);
            }
            //如果有新存入的商品详情图，则将原来的删除，并添加新的图片
            if (productHolderList != null && productHolderList.size() > 0) {
                deleteProductImgList(product.getProductId());
                addProductImgList(product, productHolderList);
            }
            try {
                //更新商品信息
                int effectedNum = productDao.updateProduct(product);
                if (effectedNum <= 0) {
                    throw new RuntimeException("更新商品信息失败");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
            } catch (Exception e) {
                throw new RuntimeException("更新商品信息失败:" + e.getMessage());
            }
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    /**
     * 批量某个商品下的所有详情图
     * @param productId
     */
    private void deleteProductImgList(Long productId) {
        //根据productId来获取原来的图片
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        //干掉原来的图片
        for (ProductImg productImg : productImgList) {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        //删除数据库里的原有图片的信息
        productImgDao.deleteProductImgByProductId(productId);
    }

    /**
     * 批量添加图片
     * @param product
     * @param productImgHolderList
     */
    private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
        //获取图片存储路径，这里直接放入相应店铺的文件夹底下
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
            List<ProductImg> productImgList = new ArrayList<ProductImg>();
            //遍历一次图片去处理，并添加ProductImg实体类中
            for (ImageHolder productImgHolder : productImgHolderList) {
                String imgAddr= ImageUtil.generateNormalImgs(productImgHolder, dest);
                ProductImg productImg = new ProductImg();
                productImg.setImgAddr(imgAddr);
                productImg.setProductId(product.getProductId());
                productImg.setCreateTime(new Date());
                productImgList.add(productImg);
            }
            if (productImgList.size()>0)
            {
                try {
                    int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                    if (effectedNum <= 0) {
                        throw new RuntimeException("创建商品详情图片失败");
                    }
                } catch (Exception e) {
                    throw new RuntimeException("创建商品详情图片失败:" + e.getMessage());
                }
        }
    }

    /**
     * 添加缩略图
     * @param product
     * @param thumbnail
     */
    @Transactional
    public void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }
}
