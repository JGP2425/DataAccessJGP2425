package com.jgp.OnlineMarket.OnlineMarket.models.dao;

import com.jgp.OnlineMarket.OnlineMarket.models.entities.ProductEntity;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerEntity;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ISellerProductEntityDAO extends CrudRepository<SellerProductEntity, Integer> {
    List<SellerProductEntity> getSellerProductsBySeller(SellerEntity seller);
    SellerProductEntity getSellerProductByProductAndSeller(ProductEntity product, SellerEntity seller);
    @Query("SELECT s FROM SellerProductEntity s WHERE s.seller = :seller " +
            "AND ((s.offerStartDate BETWEEN :startDate AND :endDate) " +
            "OR (s.offerEndDate BETWEEN :startDate AND :endDate) " +
            "OR (:startDate BETWEEN s.offerStartDate AND s.offerEndDate) " +
            "OR (:endDate BETWEEN s.offerStartDate AND s.offerEndDate))")
    List<SellerProductEntity> findOverlappingOffers(@Param("seller") SellerEntity seller,
                                                    @Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate);

}
