function transfer(json){
  var obj = JSON.parse(json);
  var newJson = {
    string1: obj.city + obj.area,
    string2: obj.resblock + obj.unit
  };

  var list = [];

  var listData = obj.list;
  for (var i in listData){
    var item = listData[i];

    var tv = {};

    var title = {
       title1 : item.agent_name + item.agent_id,
       title2 : obj.city + item.area_name
    };

    var image = {
       image1 : item.avatar
    };

    tv.title = title;
    tv.image = image;

    list.push(tv);
  }

  newJson.list = list;

  return JSON.stringify(newJson);
}