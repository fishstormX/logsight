window.currentPage = 0
window.pages = 0
function initSpiritPagination(currentPage, pages){
    window.currentPage = currentPage;
    window.pages = pages;
    var html = '';
    var commonHead = '<div class="justify-content-center pagination">' +
        '<ul class="pagination">'
        if(currentPage==1||currentPage==0) {
         commonHead = commonHead+ '<li class="page-item active"><a class="page-link" href="JavaScript:void(0);">1</a></li>';
        }else{
            commonHead = commonHead+'<li class="page-item page-pre"><a class="page-link" href="JavaScript:void(0);">‹</a></li>'+
                '<li class="page-item"><a class="page-link" href="JavaScript:void(0);">1</a></li>';
        }
    if(pages <= 7&&page>1){
        html =  commonHead;
        for(var i=1; i< pages; i++){
            if(i===currentPage){
                html += '<li class="page-item active"><a class="page-link" href="JavaScript:void(0);">'+(i+1)+'</a></li>';
            }else {
                html += '<li class="page-item"><a class="page-link" href="JavaScript:void(0);">' + (i + 1) + '</a></li>';
            }
        }
        html += '<li class="page-item page-next"><a class="page-link" href="JavaScript:void(0);">›</a></li>'+
            '</ul></div>';
    }else if(pages > 7){
        html =  commonHead;
        if(currentPage<5){
            for(var i=1; i< 5; i++){
                if(i+1===currentPage){
                    html += '<li class="page-item active"><a class="page-link" href="JavaScript:void(0);">'+(i+1)+'</a></li>';
                }else {
                    html += '<li class="page-item"><a class="page-link" href="JavaScript:void(0);">' + (i+1) + '</a></li>';
                }
            }
            html += '<li class="page-item page-last-separator disabled"><a class="page-link" href="JavaScript:void(0);">...</a></li>'
        }else if(currentPage<pages-3){
            html += '<li class="page-item page-last-separator disabled"><a class="page-link" href="JavaScript:void(0);">...</a></li>'+
                 '<li class="page-item"><a class="page-link" href="JavaScript:void(0);">'+(currentPage-1)+'</a></li>'+
                '<li class="page-item active"><a class="page-link" href="JavaScript:void(0);">'+currentPage+'</a></li>'+
                '<li class="page-item"><a class="page-link" href="JavaScript:void(0);">'+(currentPage+1)+'</a></li>'+
                '<li class="page-item page-last-separator disabled"><a class="page-link" href="JavaScript:void(0);">...</a></li>'
        }else{
            html += '<li class="page-item page-last-separator disabled"><a class="page-link" href="JavaScript:void(0);">...</a></li>'
            for(var i=4; i>0; i--){
                if((pages-i)===currentPage){
                    html += '<li class="page-item active"><a class="page-link" href="JavaScript:void(0);">'+(pages-i)+'</a></li>';
                }else {
                    html += '<li class="page-item"><a class="page-link" href="JavaScript:void(0);">' + ((pages-i)) + '</a></li>';
                }
            }
        }
        if(pages===currentPage){
            html += '<li class="page-item active"><a class="page-link" href="JavaScript:void(0);">'+pages+'</a></li>'+
                '</ul></div>';
        }else{
            html += '<li class="page-item"><a class="page-link" href="JavaScript:void(0);">'+pages+'</a></li>'+
                '<li class="page-item page-next"><a class="page-link" href="JavaScript:void(0);">›</a></li>'+
                '</ul></div>';
        }
    }
    $(".spiritPagination").html(html);
}
$(document).on("click",'.page-item',function () {
    let paged=$(this).text()
    if(!isNaN(paged)){
        console.log(paged)
        onPageClick(parseInt(paged));
    }else if(paged==="‹"){
        onPageClick(window.currentPage-1);
    }else if(paged==="›"){
        onPageClick(window.currentPage+1);
    }
});