package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;


import com.example.entity.Account;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Account createAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isEmpty() ||
            account.getPassword() == null || account.getPassword().length() < 4) {
                throw new IllegalArgumentException("Username or password is invalid");
            }

        if (accountRepository.findAccountByUsername(account.getUsername()) != null) {
            throw new IllegalArgumentException("Account already exsits");
        }

        accountRepository.save(account);
        return accountRepository.findAccountByUsername(account.getUsername());
    }

    public Account login (String username, String password) {
        Account account = accountRepository.findAccountByUsername(username);
        if (account != null && password == account.getPassword()) {
            return accountRepository.findAccountByUsernameAndPassword(username,password);
        }
        throw new IllegalArgumentException("Username or password is incorrect");
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountByUsername(String usr) {
        if (accountRepository.findAccountByUsername(usr) == null) {
            throw new IllegalArgumentException("username not found");
        }

        return accountRepository.findAccountByUsername(usr);
    }

    public Account deleteAccount(int id) {
        Account account = accountRepository.findAccountByAccountId(id);
        if (account == null) {
            throw new IllegalArgumentException("account not found");
        }
        accountRepository.deleteAccountByAccountId(id);
        return account;
    }

}
