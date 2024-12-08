package com.example.samsung_delivery.repository;

import com.example.samsung_delivery.dto.dashboard.*;
import com.example.samsung_delivery.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select new com.example.samsung_delivery.dto.dashboard.DailySummaryResponseDto(count(o), sum(o.totalPrice)) " +
            "from Order o " +
            "where o.modifiedAt >= :startOfDay and o.modifiedAt < :endOfDay")
    DailySummaryResponseDto findByModifiedAt(LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Query("select new com.example.samsung_delivery.dto.dashboard.MonthlySummaryResponseDto(count(o), sum(o.totalPrice)) " +
            "from Order o " +
            "where o.modifiedAt >= :startMonth and o.modifiedAt < :endMonth")
    MonthlySummaryResponseDto findByMonth(LocalDateTime startMonth, LocalDateTime endMonth);

    @Query("select new com.example.samsung_delivery.dto.dashboard.StoreDailyResponseDto(m.store.id, count(o), sum(o.totalPrice)) " +
            "from Order o " +
            "join o.menu m " +
            "where o.modifiedAt >= :startOfDay and o.modifiedAt < :endOfDay " +
            "group by m.store")
    List<StoreDailyResponseDto> findByStore(LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Query("select new com.example.samsung_delivery.dto.dashboard.StoreMonthlyResponseDto(m.store.id, count(o), sum(o.totalPrice)) " +
            "from Order o " +
            "join o.menu m " +
            "where o.modifiedAt >= :startMonth and o.modifiedAt < :endMonth "
            + "group by m.store")
    List<StoreMonthlyResponseDto> findByStoreMonthly(LocalDateTime startMonth, LocalDateTime endMonth);

    @Query("select new com.example.samsung_delivery.dto.dashboard.CategoryDailyResponseDto(m.store.category, count(o), sum(o.totalPrice)) " +
            "from Order o " +
            "join o.menu m " +
            "where o.modifiedAt >= :startOfDay and o.modifiedAt < :endOfDay " +
            "group by m.store.category")
    List<CategoryDailyResponseDto> findByCategoryDaily(LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Query("select new com.example.samsung_delivery.dto.dashboard.CategoryMonthlyResponseDto(m.store.category, count(o), sum(o.totalPrice)) " +
            "from Order o " +
            "join o.menu m " +
            "where o.modifiedAt >= :startMonth and o.modifiedAt < :endMonth "
            + "group by m.store.category")
    List<CategoryMonthlyResponseDto> findByCategoryMonthly(LocalDateTime startMonth, LocalDateTime endMonth);
}
