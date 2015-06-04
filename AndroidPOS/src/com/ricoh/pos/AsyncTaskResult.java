package com.ricoh.pos;

public class AsyncTaskResult<T> {
	private T content;
	private int resourceId;
	private boolean isError;

	private AsyncTaskResult(T content, boolean isError, int resourceId) {
		this.content = content;
		this.isError = isError;
		this.resourceId = resourceId;
	}

	public T getContent() {
		return content;
	}

	public boolean isError() {
		return isError;
	}

	public int getResourceId() {
		return resourceId;
	}

	public static <T> AsyncTaskResult<T> createNormalResult(T content) {
		return new AsyncTaskResult<T>(content, false, 0);
	}

	public static <T> AsyncTaskResult<T> createErrorResult(int resourceId) {
		return new AsyncTaskResult<T>(null, true, resourceId);
	}
}
