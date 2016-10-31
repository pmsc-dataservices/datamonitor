package base.util;

import java.util.ArrayList;
import java.util.List;

/**
 * List向Page的转化
 * 构造时将list加入page输入需要的页数就可以完成
 * 适用于结果集需要处理过滤的查询结果
 * 
 * @param <T> Page中记录的类型.
 * @author Killler
 * 
 * 已经测试 测试员 Olivia
 */

public class UnSearchPage<T> {

	public static final int MIN_PAGESIZE = 1;
	public static final int MAX_PAGESIZE = 200;
	// 分页参数
	private int pageNo = 1;
	// 总页数
	private int totalCount = -1;
	// 分页的结果集
	private List<T> result = null;
	// 每页的页数
	private int pageSize = MIN_PAGESIZE;

	// 构造函数
	public UnSearchPage(List<T> result,int pagesize) {
		this.result = result;
		this.pageSize = pagesize;
		if(result.size()%pagesize == 0)
			this.totalCount = result.size()/pagesize-1;
		else
			this.totalCount = (result.size()/pagesize);
	}
	
	public UnSearchPage(List<T> result) {
		this.result = result;
		this.pageSize = 10;
		if(result.size()%10 == 0)
			this.totalCount = result.size()/10-1;
		else
			this.totalCount = (result.size()/10);
	}
	/**
	 * 每页页数
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * 获取当前页数
	 */
	public int getPageNo() {
		return pageNo;
	}
	/**
	 * 放当前页
	 * @param pageNo 当前页
	 */
	public void setPageNo(int pageNo) {
		if(pageNo > MIN_PAGESIZE && pageNo < MAX_PAGESIZE)
			this.pageNo = pageNo;
		else
			this.pageNo = 1;
	}
	/**
	 * 前一页
	 */
	public int getPrePage() {
		if (pageNo == 1)
			return this.pageNo;
		else {
			return this.pageNo-1;
		}
	}
	/**
	 * 后一页
	 */
	public int getNextPage() {
		if (this.pageNo + 1 > this.totalCount)
			return this.totalCount+1;
		else {
			return this.pageNo+1;
		}
	}
	/**
	 * 总页数 
	 */
	public int getTotalCount() {
		return totalCount+1;
	}
	/**
	 * 返回当前页的结果
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> getResult() {
		List<T> nowPage = new ArrayList();
		for(int i=0;i < this.result.size();i++)
		{
			if(i >= (this.pageSize*(this.pageNo-1)) && i < (this.pageSize*this.pageNo))
				nowPage.add(this.result.get(i));
		}
		return nowPage;
	}
	
	/**
	* 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始.
	*/
	public int getFirst() {
		return ((this.pageNo - 1) * this.pageSize);
	}
	
}
