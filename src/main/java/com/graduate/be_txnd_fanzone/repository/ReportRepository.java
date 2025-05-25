package com.graduate.be_txnd_fanzone.repository;

import com.graduate.be_txnd_fanzone.model.ReportPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<ReportPost, Long> {

    Page<ReportPost> findByStatus(Byte status, Pageable pageable);

    Optional<ReportPost> findByReportId(Long reportId);
}
