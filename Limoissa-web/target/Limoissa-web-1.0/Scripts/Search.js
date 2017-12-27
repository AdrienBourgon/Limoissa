$(function() {    
    // Clic sur un lien pour la partie admin
    $('#SearchResults tbody tr td a').click(function(e) {
        e.stopPropagation();
    });
    
    // Clic sur un lien pour la pagination
    $("#pagination a").click(function() {
        $('#SearchResults').data('page', $(this).text());
        redirect();
        
        return false;
    });

    // Clic sur un des th pour le tri
    $("#SearchResults thead a").click(function() {
        var tab = $('#SearchResults');
        var Direction = tab.data('orderbydirection');
        var OrderBy = tab.data('orderby');
        
        tab.data('orderbydirection', OrderBy === $(this).data("target") ? Direction === 'ASC' ? 'DESC' : 'ASC' : 'ASC');
        tab.data('orderby', $(this).data("target"));
        redirect();
        
        return false;
    });
    
    //Reditection (pagination, tri)
    function redirect() {
        var tab = $('#SearchResults');
        var title = tab.data("title"), page = tab.data("page");
        var orderBy = tab.data("orderby"), orderByDirection = tab.data("orderbydirection");
        
        document.location.href = '?S_Title='+title+'&S_Page='+page+(orderBy.length > 0 ? ('&S_OrderBy='+orderBy+'&S_OrderBy_Direction='+orderByDirection) : '');
    }
    // Cic sur une ligne pour afficher le résumé
    $('#SearchResults .Book').click(function() {
       // Afficher/Masquer la ligne de description
       $(this).next().toggle();
    });
});