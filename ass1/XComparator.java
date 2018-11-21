
public class XComparator implements Comparator
{
	public int compare( Object a , Object b )
	{
		if( ! ( a instanceof Point ) | ! ( b instanceof Point ) )
			throw new IllegalArgumentException();
		
		int xa = ( ( Point ) a ).getX();
		int xb = ( ( Point ) b ).getX();
		
		if( xa > xb )
			return -1;
		
		if( xa < xb )
			return 1;

		return 0;
	}

}

