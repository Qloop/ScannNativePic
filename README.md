# ScannNativePic
查询手机的存储中所有后缀为jpg的文件，查询成功后将所有文件的文件名和图片显示到一个RecyclerView中。（不使用ContentResolver类实现）    具体要求：
* 不可使用第三方库    
* 使用线程池   
* 查询速度尽可能快    
* UI的展示尽量流畅

对的 你猜对了  这是一个笔试要求，以前做过了 代码就在上两个仓库里，但是这次……  竟然不让我用ContentResolver……说实话  我一下没明白  对方的意图   但还是写一下吧。

嗯……考了两周的试，终于有时间写了……
没有用ContentProvider，使用线程池遍历手机文件，找到".jpg"图片，使用NativeImageLoader加载(LruCache缓存、比例裁剪避免OOM)。本机测试700张图片流畅扫描加载。
