package io.protostuff.parser.reader;

import org.antlr.v4.runtime.CharStream;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ClasspathFileReaderTest {

    @Test
    public void testRead() throws Exception {
        ClasspathFileReader reader = new ClasspathFileReader();
        CharStream a = reader.read("test/test.proto");
        CharStream b = reader.read("this_file_does_not_exist.proto");
        assertNotNull(a);
        assertNull(b);
    }
}
