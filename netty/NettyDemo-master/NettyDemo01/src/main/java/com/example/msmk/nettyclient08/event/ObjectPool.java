package com.example.msmk.nettyclient08.event;

/**
 * �����
 *
 * Created by Freddy on 2015/11/3.
 * chenshichao@outlook.com
 */
public abstract class ObjectPool<T extends PoolableObject> {
    /**
     * ��������
     */
    private T[] mContainer;

    private final Object mLock = new Object();

    /**
     * ÿ�η��ض��󶼷ŵ�����ĩ�ˣ�mLength��ʾǰ����ö�����
     */
    private int mLength;

    public ObjectPool(int capacity) {
        mContainer = createObjPool(capacity);
    }

    /**
     * ���������
     * @param capacity
     *                  ����޶�����
     * @return
     *                  �����
     */
    protected abstract T[] createObjPool(int capacity);

    /**
     * ����һ���µĶ���
     * @return
     *          �����ɹ��Ķ���
     */
    protected abstract T createNewObj();

    /**
     * �Ӷ�������̳�һ����������������������´���һ������
     * @return
     *          �̳������´����Ķ���
     */
    public final T get() {
        T obj = findFreeObject();
        if(null == obj) {
            obj = createNewObj();
        }else {
            // �������״̬
            obj.reset();
        }

        return obj;
    }

    /**
     * �Ѷ���Żس�����
     * @param obj
     *              ��Ҫ�Żض���صĶ���
     */
    public final void returnObj(T obj) {
        synchronized (mLock) {
            int size = mContainer.length;
            if(mLength < size) {
                mContainer[mLength] = obj;
                mLength++;
            }
        }
    }

    /**
     * �ӳ����ҵ����еĶ���
     * @return
     *          ���еĶ���
     */
    private T findFreeObject() {
        T obj = null;
        synchronized (mLock) {
            if(mLength > 0) {
                --mLength;
                obj = mContainer[mLength];
                // ��ֵ��ɺ��ͷ���Դ
                mContainer[mLength] = null;
            }
        }
        return obj;
    }
}
