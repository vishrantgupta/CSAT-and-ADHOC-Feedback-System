var MapsGoogle=function(){var t=function(){new GMaps({div:"#gmap_basic",lat:-12.043333,lng:-77.028333})},e=function(){var t=new GMaps({div:"#gmap_marker",lat:-12.043333,lng:-77.028333});t.addMarker({lat:-12.043333,lng:-77.03,title:"Lima",details:{database_id:42,author:"HPNeo"},click:function(t){console.log&&console.log(t),alert("You clicked in this marker")}}),t.addMarker({lat:-12.042,lng:-77.028333,title:"Marker with InfoWindow",infoWindow:{content:'<span style="color:#000">HTML Content!</span>'}})},i=function(){var t=new GMaps({div:"#gmap_polylines",lat:-12.043333,lng:-77.028333,click:function(t){console.log(t)}});path=[[-12.044012922866312,-77.02470665341184],[-12.05449279282314,-77.03024273281858],[-12.055122327623378,-77.03039293652341],[-12.075917129727586,-77.02764635449216],[-12.07635776902266,-77.02792530422971],[-12.076819390363665,-77.02893381481931],[-12.088527520066453,-77.0241058385925],[-12.090814532191756,-77.02271108990476]],t.drawPolyline({path:path,strokeColor:"#131540",strokeOpacity:.6,strokeWeight:6})},s=function(){var t=new GMaps({div:"#gmap_geo",lat:-12.043333,lng:-77.028333});GMaps.geolocate({success:function(e){t.setCenter(e.coords.latitude,e.coords.longitude)},error:function(t){alert("Geolocation failed: "+t.message)},not_supported:function(){alert("Your browser does not support geolocation")},always:function(){}})},n=function(){var t=new GMaps({div:"#gmap_geocoding",lat:-12.043333,lng:-77.028333}),e=function(){var e=$.trim($("#gmap_geocoding_address").val());GMaps.geocode({address:e,callback:function(e,i){if("OK"==i){var s=e[0].geometry.location;t.setCenter(s.lat(),s.lng()),t.addMarker({lat:s.lat(),lng:s.lng()}),App.scrollTo($("#gmap_geocoding"))}}})};$("#gmap_geocoding_btn").click(function(t){t.preventDefault(),e()}),$("#gmap_geocoding_address").keypress(function(t){var i=t.keyCode?t.keyCode:t.which;"13"==i&&(t.preventDefault(),e())})},r=function(){var t=new GMaps({div:"#gmap_polygons",lat:-12.043333,lng:-77.028333}),e=[[-12.040397656836609,-77.03373871559225],[-12.040248585302038,-77.03993927003302],[-12.050047116528843,-77.02448169303511],[-12.044804866577001,-77.02154422636042]];t.drawPolygon({paths:e,strokeColor:"#BBD8E9",strokeOpacity:1,strokeWeight:3,fillColor:"#BBD8E9",fillOpacity:.6})},o=function(){var t=new GMaps({div:"#gmap_routes",lat:-12.043333,lng:-77.028333});$("#gmap_routes_start").click(function(e){e.preventDefault(),App.scrollTo($(this),400),t.travelRoute({origin:[-12.044012922866312,-77.02470665341184],destination:[-12.090814532191756,-77.02271108990476],travelMode:"driving",step:function(e){$("#gmap_routes_instructions").append("<li>"+e.instructions+"</li>"),$("#gmap_routes_instructions li:eq("+e.step_number+")").delay(800*e.step_number).fadeIn(500,function(){t.setCenter(e.end_location.lat(),e.end_location.lng()),t.drawPolyline({path:e.path,strokeColor:"#131540",strokeOpacity:.6,strokeWeight:6})})}})})},a=function(){map=new GMaps({div:"#gmap_types",lat:-12.043333,lng:-77.028333,mapTypeControlOptions:{mapTypeIds:["hybrid","roadmap","satellite","terrain","osm","cloudmade"]}}),map.addMapType("osm",{getTileUrl:function(t,e){return"http://tile.openstreetmap.org/"+e+"/"+t.x+"/"+t.y+".png"},tileSize:new google.maps.Size(256,256),name:"OpenStreetMap",maxZoom:18}),map.addMapType("cloudmade",{getTileUrl:function(t,e){return"http://b.tile.cloudmade.com/8ee2a50541944fb9bcedded5165f09d9/1/256/"+e+"/"+t.x+"/"+t.y+".png"},tileSize:new google.maps.Size(256,256),name:"CloudMade",maxZoom:18}),map.setMapTypeId("osm")},l=function(){GMaps.createPanorama({el:"#gmap_panaroma",lat:42.3455,lng:-71.0983})};return{init:function(){t(),e(),s(),n(),i(),r(),o(),a(),l()}}}();