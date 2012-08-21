package com.apollo.dbfetcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.apollo.FileHelp;

/**
 * 如果是IO比较多的话，线程可以超过CPU的个数。 如果是CPU的密集型应用，一般为CPU*N*70%
 * 
 * @author dragon.caol 2012-8-21 下午5:24:53
 */
public class DbFetcher {
	static BlockingQueue<String> queue = new ArrayBlockingQueue<String>(2500);
	static BlockingQueue<String> outStringQueue = new ArrayBlockingQueue<String>(
			10000);
	static String sql = "select ali_id from CBU_MEMBER_ID_MAPPING_new where member_id=? and rownum=1";
	static String filePre = "D:\\资料\\项目文档\\换mid\\数据提取\\王义东 需要匹配ID\\";
	static String file = "续签统计2013年（2012-8-10）_master5";
	static AtomicInteger count = new AtomicInteger(0);

	static class Producer implements Runnable {
		public void run() {
			BufferedReader br = null;
			try {
				br = FileHelp.getBufferedReader(filePre + file + ".txt");
				String line = null;
				while ((line = br.readLine()) != null) {
					if (line == null || line.trim().equals("")) {

					} else {
						queue.put(line);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("Producer exit");
			}
		}
	}

	static class Consumer implements Runnable {
		public void run() {
			Connection con = DBConnection.getConnection();
			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement(sql);
				while (true) {
					String mid = queue.poll(5, TimeUnit.SECONDS);
					if (mid == null) {
						break;
					}
					pstmt.setString(1, mid);
					ResultSet set = pstmt.executeQuery();
					String ali_id = null;
					while (set.next()) {
						ali_id = set.getString("ali_id");
					}
					if (ali_id == null) {
						System.err.println(mid);
					} else {
						count.getAndIncrement();
						outStringQueue.put(mid + "\t" + ali_id);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				try {
					pstmt.close();
					con.close();
					System.out.println("Consumer exit");
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
	}

	static class FileBacker implements Runnable {
		public void run() {
			PrintWriter out = null;
			try {
				out = FileHelp.getPrintWriter(filePre + file + ".r.txt");
				while (true) {

					String str = outStringQueue.poll(5, TimeUnit.SECONDS);
					if (str == null) {
						out.flush();
						break;
					} else {
						out.println(str);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				out.flush();
				out.close();
				System.out.println("FileBacker exit");
			}
		}
	}

	public static void main(String[] args) throws SQLException, IOException,
			InterruptedException {
		long start = System.currentTimeMillis();
		ExecutorService executor = Executors.newCachedThreadPool();
		List<Future<?>> li = new ArrayList<Future<?>>();
		li.add(executor.submit(new Producer()));
		for (int i = 0; i < 10; i++) {
			li.add(executor.submit(new Consumer()));
		}
		li.add(executor.submit(new FileBacker()));
		executor.shutdown();
		int time = 0;
		int number = 0;
		while (true) {
			Boolean flag = true;
			for (Future<?> future : li) {
				if (!future.isDone()) {
					flag = false;
					break;
				}
			}
			if (flag) {
				break;
			}
			time++;
			Thread.sleep(1000);
			int new_number = count.get();
			System.out
					.println("time:" + time + ",qps:" + (new_number - number)
							+ ",queue:" + queue.size() + "--->"
							+ outStringQueue.size());
			number = new_number;
		}
		long end = System.currentTimeMillis();
		System.out.println((end - start) / 1000);
	}
}
