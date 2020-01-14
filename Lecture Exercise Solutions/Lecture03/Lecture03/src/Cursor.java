
/*** Exercise 1 ***/

public interface Cursor<I> {

	/**
	 * Checks whether the cursor is positioned at an element in the collection.
	 * @return true if the cursor is positioned at an element, false otherwise.
	 */
	boolean itemExists();
	
	/** Get the item at the cursor 
	 *  @return The element at which the cursor is positioned.
	 *  @throws Throws an exception if the cursor is not positioned at any item.
	 **/
	public I item() throws RuntimeException;
	 
	/**	
	 * Go to the first item in the structure. 
	 * @precond !isEmpty()
	 * @throws RuntimeException
	 */
	public void goFirst() throws RuntimeException;

	/**	Advance one item in the data structure. 
	 * @precond  !after()
	 * @throws RuntimeException
     */
	public void goForth() throws RuntimeException;
	
	/**	
	 * Go to the last item in the structure. 
	 * @precond !isEmpty()
	 * @throws RuntimeException
	 */
	public void goLast() throws RuntimeException;
	
	/**	Move to the position prior to the first element in the structure. */
	public void goBefore();

	/**	Move to the position after the last element in the structure. */
	public void goAfter() throws RuntimeException;
	
	/**	Is the current position before the start of the structure?. */
	public boolean before();
 
	/**	Is the current position after the end of the structure?. */
	public boolean after();

}
