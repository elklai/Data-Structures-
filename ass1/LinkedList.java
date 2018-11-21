import java.util.Iterator;
public class LinkedList implements List 
{

	protected DLink first;
	protected DLink last;
	protected int size;
	
	
	protected DLink getFirst()
	{
		return this.first;
	}
	
	
	protected DLink getLast()
	{
		return this.last;
	}
	
	
	protected int getSize()
	{
		return this.size;
	}
	
	
	public LinkedList()
	{
		first = null;
		last = null;
		size = 0;
	}

	
	public void sortBy( Comparator comp )
	{
		if( comp == null )
			throw new NullPointerException();
	
		if( this.size() > 1 )
		{
			for( int i = this.size() - 1 ; i > 0 ; i = i - 1 )
			{
				DLink current = this.getFirst();
				DLink next = current.getNext();
				for( int j = 0 ; j < i ; j = j + 1 )
				{
					if( comp.compare( current.getData() , next.getData() ) > 0 )
						current.setData( next.setData( current.getData() ) );
				 
					current = current.getNext();
					next = next.getNext();
				}
			}
		}
	}

	
	public String toString() 
	{
		return ( "description:\n" + "all " + this.size() + " objects are saved" );
	}

	public boolean equals( Object other ) 
	{  
		if( ( ! ( other instanceof LinkedList ) ) || ( ( LinkedList ) other ).size() != this.size() )
			return false;
		
		boolean output = true;
		Iterator iterThis = this.iterator();
		Iterator iterOther = ( ( LinkedList ) other ).iterator();
		
		for( int i = 0 ; i < this.size() & output ; i = i + 1 )
			output = ( iterThis.next() ).equals( iterOther.next() );
		
		return output;
	}

	public DLink add( Object element ) 
	{
		nullCheck( element );
		if( isEmpty() )
		{
			first = new DLink( element );
			last = first;
			this.size++;
			return first;
		}
		else 
		{
			DLink newLast = new DLink( element , null , last );
			last = newLast;
			this.size++;
			return newLast;
		}				
	}

	
	@Override
	public int size() 
	{
		return size;
	}

	
	@Override
	public boolean contains(Object element) 
	{
		for( Object obj : this )
			if( ( obj ).equals( element ) )
				return true;
		
		return false;
	}

	
	@Override
	public boolean isEmpty() 
	{
		return ( this.size() == 0 );
	}

	
	@Override
	public Object get( int index ) 
	{
		rangeCheck( index );
		LinkedListIterator iterGet = ( LinkedListIterator ) this.iterator();
		Object output = null;
		
		for( int i = 0 ; i <= index ; i++ )
			output = iterGet.next();
		
		return output;
	}

	public Object get( Object element )
	{
		if( element == null )
			throw new RuntimeException( "null value" );
		LinkedListIterator iterGet = ( LinkedListIterator )this.iterator();
		
		for( Object curr : this )
			if( ( ( DLink ) curr ).getData().equals( element ) )
				return curr;
		
		return null;
	}
	
	
	@Override
	public Object set( int index , Object element )
	{
		DLink a = ( DLink ) this.get( index );
		
		return a.setData( element );
	}

	
	@Override
	public Iterator iterator() 
	{
		return new LinkedListIterator( this.getFirst() );
	}

	// throws an exception if the given index is not in range
	private void rangeCheck( int index )
	{
		if( index < 0 || index >= size() )
	        throw new IndexOutOfBoundsException( "Index: " + index + ", Size: " + size() );
	}
	
	// throws an exception if the given element is null
	private void nullCheck( Object element )
	{
		if ( element == null )
			throw new NullPointerException();
	}
	
	public void remove( DLink link )
	{
		if( link == null )
			throw new RuntimeException( "null value" );
		
		if( link == first && last == first )
		{
			first=null;
			last=null;
		}
		else
		{
			if( link == last )
			{
				last = link.getPrev();
				last.setNext( null );
			}
			else 
				if( link == first )
				{
					first = link.getNext();
					first.setPrev( null );
				}
				else
				{
					link.getPrev().setNext( link.getNext() );
					link.getNext().setPrev( link.getPrev() );
				}
		
		
		}
		link.setNext( null );
		link.setPrev( null );
	}
	
}
