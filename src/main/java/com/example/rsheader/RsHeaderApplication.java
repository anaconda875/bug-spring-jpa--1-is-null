package com.example.rsheader;

import com.example.rsheader.entity.Test;
import com.example.rsheader.entity.TestHistory;
import com.example.rsheader.repository.TestHistoryRepository;
import com.example.rsheader.repository.TestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SpringBootApplication
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
@EnableScheduling
public class RsHeaderApplication {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();
    private static final BlockingQueue<TestHistory> HISTORIES = new LinkedBlockingDeque<>();

    private final TestRepository testRepository;
    private final TestHistoryRepository testHistoryRepository;
    private final TransactionHelper transactionHelper;

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @GetMapping("/ab")
    public Page<Test> ab(CustomPageable pageable) {

//        testRepository.saveAll(List.of(new Test(2L, "a"), new Test(3L, "b"), new Test(4L, "c")));
//        Test test = testRepository.findById(3L).get();
//        test.setName("aa");
//        testRepository.save(test);
        return testRepository.findAll((String) null, pageable);
    }

    public static class CustomPageable extends PageRequest {

        @Getter
        private final String keyword;

        /**
         * Creates a new {@link PageRequest} with sort parameters applied.
         *
         * @param page zero-based page index, must not be negative.
         * @param size the size of the page to be returned, must be greater than 0.
         * @param sort must not be {@literal null}, use {@link Sort#unsorted()} instead.
         */
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

//		String t = "(%d, '%s', %d)";
//		List<String> q = new ArrayList<>();
//		StringBuilder builder = new StringBuilder();
//		for(int i = 1; i <= 1000000; i++) {
//			q.add(String.format(t, i, "name " + i, 0));
//			if(i % 10000 == 0) {
//				builder.append("START TRANSACTION;\n")
//						.append("INSERT INTO test VALUES ").append(String.join(",", q))
//						.append(";\nCOMMIT;\n");
//				q.clear();
//			}
//		}
//
//		Files.write(Path.of("d:/insert2_test.sql"), builder.toString().getBytes(StandardCharsets.UTF_8));


//		String t = "name='%s', status = %d";
//		List<String> q = new ArrayList<>();
//		StringBuilder builder = new StringBuilder();
//		for(int i = 1; i <= 1000000; i++) {
//			if(i % 10000 == 1) {
//				builder.append("START TRANSACTION;\n");
//			}
//			builder.append("UPDATE test SET ").append(String.format(t, "aname " + i, 1))
//					.append(" WHERE name = 'name ").append(i).append("';\n");
//			if(i % 10000 == 0) {
//				builder.append("COMMIT;\n");
//			}
//		}
//
//		Files.write(Path.of("d:/update_test.sql"), builder.toString().getBytes(StandardCharsets.UTF_8));


//		String t = "status = %d";
//		List<String> q = new ArrayList<>();
//		StringBuilder builder = new StringBuilder();
//		for(int i = 1; i <= 1000000; i++) {
//			q.add("id = " + i);
//			if(i % 10000 == 0) {
//				builder.append("START TRANSACTION;\n")
//						.append("UPDATE test SET ")
//						.append(String.format(t, 0))
//						.append(" WHERE ")
//						.append(String.join(" or ", q))
//						.append(";\nCOMMIT;\n");
//				q.clear();
//			}
//		}
//
//		Files.write(Path.of("d:/update4_test.sql"), builder.toString().getBytes(StandardCharsets.UTF_8));


        SpringApplication.run(RsHeaderApplication.class, args);

//        String collect = Files.readAllLines(Path.of("c:/Users/hflbt/Desktop/pc.txt")).stream().map(String::strip)
//                .map(n -> "SHOW COLUMNS FROM " + n + ";").collect(Collectors.joining("\n"));
//
//        System.out.println(collect);
    }

//	@GetMapping("/aa")
//	public void aa() {
//		List<Test> ts = new ArrayList<>();
//		System.out.println("Start: " + System.currentTimeMillis());
//		for(int i = 1; i <= 1000; i++) {
//			ts.add(new Test("name " + i, 0));
//			if(i % 100 == 0) {
//				List<Test> rs = transactionHelper.executeSeparately(() -> testRepository.saveAll(ts));
//				new Thread(() -> {
//					List<TestHistory> h = rs.stream().map(tb -> new TestHistory(tb, buildChanges(tb))).collect(Collectors.toList());
//					transactionHelper.executeSeparately(() -> testHistoryRepository.saveAll(h));
//				}).start();
//
//				ts.clear();
//			}
//		}
//		System.out.println("End: " + System.currentTimeMillis());
//	}
//
//	@SneakyThrows
//	private String buildChanges(Test tb) {
//		List<Map<String, Object>> rs = new ArrayList<>();
//		Map<String, Object> map1 = new LinkedHashMap<>();
//		map1.put("column", "name");
//		map1.put("old", null);
//		map1.put("new", tb.getName());
//
//		Map<String, Object> map2 = new LinkedHashMap<>();
//		map2.put("column", "status");
//		map2.put("old", null);
//		map2.put("new", tb.getStatus());
//
//		rs.add(map1);
//		rs.add(map2);
//
//		return new ObjectMapper().writeValueAsString(rs);
//	}

//    static AtomicInteger i = new AtomicInteger(1);
//
//    @Bean
//    public Consumer<Message<Event>> testChanges() {
//        return msg -> {
//            try {
//                Event e = msg.getPayload();
//                Event.Payload payload = e.getPayload();
//                String op = payload.getOp();
//                int andIncrement = i.getAndIncrement();
////                if(andIncrement % 2000 == 0)
//                    System.out.println(andIncrement);
//
//                TestHistory history = buildHistory(payload);
//                HISTORIES.offer(history);
//
////				testHistoryRepository.save(history);
//
//            } catch(ClassCastException e) {
//                System.err.println(e.getMessage());
//                System.err.println(msg.getPayload());
//            }
//        };
//    }
//
//    @SneakyThrows
//    private TestHistory buildHistory(Event.Payload payload) {
//        TestHistory history = new TestHistory();
//        Map<String, Object> before = payload.getBefore();
//        Map<String, Object> after = payload.getAfter();
//
//
//        List<Map<String, Object>> rs = new ArrayList<>();
//        if(before == null || before.isEmpty()) {
//            history.setTest(new Test(Long.valueOf(after.get("id").toString())));
//            after.forEach((k, v) -> {
//                Map<String, Object> change = new LinkedHashMap<>();
//                change.put("column", k);
//                change.put("old", null);
//                change.put("new", v);
//                rs.add(change);
//            });
//        } else if(after == null || after.isEmpty()) {
//            before.forEach((k, v) -> {
//                Map<String, Object> change = new LinkedHashMap<>();
//                change.put("column", k);
//                change.put("old", v);
//                change.put("new", null);
//                rs.add(change);
//            });
//        } else {
//            history.setTest(new Test(Long.valueOf(after.get("id").toString())));
//            before.forEach((k, v) -> {
//                Object newV = after.get(k);
//                if(!newV.equals(v)) {
//                    Map<String, Object> change = new LinkedHashMap<>();
//
//                    change.put("column", k);
//                    change.put("old", v);
//                    change.put("new", newV);
//                    rs.add(change);
//                }
//            });
//        }
//
//        String changes = new ObjectMapper().writeValueAsString(rs);
//        history.setChanges(changes);
//
//        return history;
//    }
//
//    @Scheduled(fixedDelay = 1000)
//    public void saveHistory() throws ExecutionException, InterruptedException, TimeoutException {
//        List<TestHistory> batch = new ArrayList<>();
//
//        Callable<Boolean> callable = () -> {
//            TestHistory h;
//            while((h = HISTORIES.poll(50, TimeUnit.MILLISECONDS)) != null) {
//                batch.add(h);
//            }
//
//            return true;
//        };
//
//		Future<Boolean> submit = EXECUTOR_SERVICE.submit(callable);
//		submit.get(3000, TimeUnit.MILLISECONDS);
//
//		if(!batch.isEmpty())
//		    transactionHelper.executeSeparately(() -> {
//		        testHistoryRepository.saveAllAndFlush(batch);
//		        em.clear();
//                batch.clear();
//
//		        return null;
//            });
//	}

}
