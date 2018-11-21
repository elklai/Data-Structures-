
public class YComparator implements Comparator
{
	public int compare( Object a , Object b )
	{
		if( ! ( a instanceof Point ) | ! ( b instanceof Point ) )
			throw new IllegalArgumentException();
		
		int ya = ( ( Point ) a ).getY();
		int yb = ( ( Point ) b ).getY();
		
		if( ya > yb )
			return -1;
		
		if( ya < yb )
			return 1;
		
		return 0;
	}

}
