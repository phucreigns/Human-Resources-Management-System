package com.phuc.hr.repository;

import com.phuc.hr.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findByPositionId(Long positionId);
    List<Position> findByDepartmentId(Long departmentId);
    List<Position> findByDepartmentIdAndStatus(Long departmentId, String status);
}


