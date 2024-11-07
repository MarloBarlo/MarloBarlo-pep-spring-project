package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Account;
import java.util.List;


public interface AccountRepository extends JpaRepository<Account,Long>{

    Account findAccountByUsernameAndPassword(String usr, String pas);

    Account findAccountByUsername(String usr);

    Account findAccountByAccountId(int id);

    Void deleteAccountByAccountId(int id);
}
