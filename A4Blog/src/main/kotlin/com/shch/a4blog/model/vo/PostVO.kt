package com.shch.a4blog.model.vo

data class PostVO(
    val postId: String,
    val title: String,
    val categorie: String = "",
    val publishDate: String = "",
    val content: String = "",
    val updateTime: String = "",
    val createTime: String = "",
)

/*
select
    GT_POST.POST_ID,
    formatdatetime(GT_POST.CREATE_TIME,'yyyy-dd') ft,
    TITLE,NAME
from GT_POST
left join PUBLIC.GT_ENTRIES_POST GEP on GT_POST.POST_ID = GEP.POST_ID
left join PUBLIC.GT_ENTRIES GE on GEP.CID = GE.CID
where ENABLE =1 and (TYPE is null or TYPE = 'CATEGORIES')
group by ft, GT_POST.POST_ID,TITLE,NAME
order by GT_POST.CREATE_TIME desc ;
 */