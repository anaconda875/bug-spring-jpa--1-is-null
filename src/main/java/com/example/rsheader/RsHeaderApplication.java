package com.example.rsheader;

import com.example.rsheader.entity.Test;
import com.example.rsheader.repository.TestRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@SpringBootApplication
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
@EnableScheduling
public class RsHeaderApplication {

    private final TestRepository testRepository;

    @GetMapping("/ab")
    public Page<Test> ab(CustomPageable pageable) {
        //INSERT INTO test VALUES(1, 'aa', 1);
        //INSERT INTO test VALUES(2, 'a', 2);
        //INSERT INTO test VALUES(3, 'b', 3);
        return testRepository.findAll(pageable.getKeyword(), pageable);
    }

    public static class CustomPageable extends PageRequest {

        @Getter
        private final String keyword;

        protected CustomPageable(int page, int size, Sort sort) {
            super(page, size, sort);
            keyword = null;
        }

        public CustomPageable(int page, int size, Sort sort, String keyword) {
            super(page, size, sort);
            this.keyword = keyword;
        }
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(RsHeaderApplication.class, args);
    }


}
