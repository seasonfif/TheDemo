function transfer(json){
    var obj = JSON.parse(json);
    var list = [];
    var newjson = {};
    
    var listData = obj.data.list;
    for (var i in listData){
        var item = listData[i];
        
        var image1 = item.cover_pic;
        var text1  = item.community_name;
        var text2  = item.blueprint_bedroom_num + '室' + item.blueprint_hall_num + '厅/' + parseInt(item.area) + '㎡'
        + item.orientation;
        
        var text3  = item.price / 10000 + '万';
        var text4  = item.unit_price + '元/平';
        
        var newItem = {
            image1: image1,
            text1 : text1,
            text2 : text2,
            text3 : text3,
            text4 : text4,
        };
        list.push(newItem);
    }
    
    newjson.list = list;
    
    return JSON.stringify(list);
}
