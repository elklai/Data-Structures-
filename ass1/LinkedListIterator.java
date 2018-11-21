import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListIterator implements Iterator 
{

	private DLink nextLink ;
	
	public LinkedListIterator( DLink start )
	{
		nextLink = start ;
	}

	
	@Override
	public boolean hasNext() 
	{
		return nextLink != null ;
	}

	
	@Override
	public Object next() 
	{
		if( !hasNext() )
			throw new NoSuchElementException();
		
		Object nextElement = nextLink ;
		nextLink = nextLink.getNext() ;
		return nextElement ;
	}
	
	
	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
	
	
}
