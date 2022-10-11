package simpleconf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import com.github.msx80.simpleconf.Configuration;

public class SimpleConfTest {

	@Test
	void filetest() throws IOException {
		Path f = Files.createTempFile("simpleconf", "test");
		
		Files.write(f, "myvalue=10".getBytes());
		
		Configuration c = Configuration.load(f);
		
		Files.write(f, "myvalue=13".getBytes());
		
		assertEquals(10, c.getInt("myvalue"));
		c.reload();
		assertEquals(13, c.getInt("myvalue"));
		
		assertEquals(c.getKeys(), new HashSet<>(Arrays.asList("myvalue")));
		
		Files.delete(f);
	}

	@Test
	void immediatetest() throws IOException {
		
		
		Configuration c = Configuration.of("mybool", "true");
		assertTrue(c.getBool("mybool"));
		
	}
	
}
