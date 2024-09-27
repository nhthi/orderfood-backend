package com.nht.repository;

import com.nht.Model.Verify;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerifyRepository extends JpaRepository<Verify,Long> {
    public Verify findByEmail(String email);
}
