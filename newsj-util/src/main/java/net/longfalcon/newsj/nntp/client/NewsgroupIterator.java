package net.longfalcon.newsj.nntp.client;

import java.util.Iterator;

/**
 * Copied from commons-net class NewsgroupIterator v3.2
 * Class which wraps an {@code Iterable<String>} of raw newgroup information
 * to generate an {@code Iterable<NewsgroupInfo>} of the parsed information.
 * @since 3.0
 */
class NewsgroupIterator implements Iterator<NewsgroupInfo>, Iterable<NewsgroupInfo> {

    private  final Iterator<String> stringIterator;

    public NewsgroupIterator(Iterable<String> iterableString) {
        stringIterator = iterableString.iterator();
    }

    public boolean hasNext() {
        return stringIterator.hasNext();
    }

    public NewsgroupInfo next() {
        String line = stringIterator.next();
        return CustomNNTPClient.__parseNewsgroupListEntry(line);
    }

    public void remove() {
        stringIterator.remove();
    }

    public Iterator<NewsgroupInfo> iterator() {
        return this;
    }
}

