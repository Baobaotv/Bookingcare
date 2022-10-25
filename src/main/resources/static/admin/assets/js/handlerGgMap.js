var kinhDo = document.getElementById("longitude");
var viDo = document.getElementById("latitude");
var valueKinhDo = Number(21.017254034683987);
var valueViDo = Number(105.7899284362793);
var marker;
initMap();
function initMap() {
    var latLng = { lat: valueKinhDo, lng: valueViDo };
    var map = new google.maps.Map(document.getElementById("map"), {
        center: latLng,
        zoom: 15,
    });
    marker = new google.maps.Marker({
        position: latLng,
        map: map,
    });

    var geocoder = new google.maps.Geocoder();
    var infowindow = new google.maps.InfoWindow();

    google.maps.event.addListener(map, "click", function (e) {
        setDataLocation(e.latLng);
        geocoder.geocode({ location: e.latLng }, function (results, status) {
            if (status === google.maps.GeocoderStatus.OK) {
                if (results[0]) {
                    infowindow.setContent(
                        "<div>" +
                        "<b>Address :</b> " +
                        results[0].formatted_address +
                        "<br>" +
                        "<b>Latitude :</b> " +
                        results[0].geometry.location.lat() +
                        "<br>" +
                        "<b>Longitude :</b> " +
                        results[0].geometry.location.lng() +
                        "</div>"
                    );
                    infowindow.open(map, marker);
                } else {
                    console.log("No results found");
                }
            } else {
                console.log("Geocoder failed due to: " + status);
            }
        });
    });

    function setDataLocation(location) {
        kinhDo.value = location.lat();
        viDo.value = location.lng();
        marker.setPosition(location);
    }
}

document.getElementById('btnSearchLocation').addEventListener("click", changeLocation);
function changeLocation() {
    valueKinhDo = Number(kinhDo.value);
    valueViDo = Number(viDo.value);
    initMap();
}