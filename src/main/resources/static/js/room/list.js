// 마커 클러스터 지도 사용

    // 0. 현재 내 위치의 위도 경도 구하기
    navigator.geolocation.getCurrentPosition(function(position) {
            var lat = position.coords.latitude, // 위도
                 lng = position.coords.longitude; // 경도

    // 1. Map 변수
    var map = new kakao.maps.Map(document.getElementById('map'), { // 지도를 표시할 div
        center : new kakao.maps.LatLng( lat , lng ), // 지도의 중심좌표 // 현재 접속된 디바이스 좌표
        level : 5 // 지도의 확대 레벨
    });

    // 2. 클러스터[ 마커 집합 ]  변수
    var clusterer = new kakao.maps.MarkerClusterer({
        map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
        averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
        minLevel: 6, // 클러스터 할 최소 지도 레벨
        disableClickZoom: true // 클러스터 마커를 클릭했을 때 지도가 확대되지 않도록 설정한다
    });

    // 데이터를 가져오기 위해 jQuery를 사용합니다
    // 데이터를 가져와 마커를 생성하고 클러스터러 객체에 넘겨줍니다
    //   $.get("통신할 URL ", function( 반환 인수 ) {

//    // 3. 여러개 MAP -> 클러스터 저장 [  모든 지도 좌표 가져오기 ]
//    $.get("/room/roomlist", function(data) {
//        console.log(data); // 통신 확인
//        // 데이터에서 좌표 값을 가지고 마커를 표시합니다
//        // 마커 클러스터러로 관리할 마커 객체는 생성할 때 지도 객체를 설정하지 않습니다
//        var markers = $(data.positions).map(function(i, position) {
//            return new kakao.maps.Marker({
//                position : new kakao.maps.LatLng( position.lat, position.lng)
//            });
//        });
//
//        // 클러스터러에 마커들을 추가합니다
//        clusterer.addMarkers(markers);
//    });

    // 6. 마커 이미지 변경
                // 마커 이미지의 주소
                var markerImageUrl = 'http://localhost:8081/img/icon_home.png',
                    markerImageSize = new kakao.maps.Size(40, 40), // 마커 이미지의 크기
                    markerImageOptions = {
                        offset : new kakao.maps.Point(20, 42)// 마커 좌표에 일치시킬 이미지 안의 좌표
                    };

                // 마커 이미지를 생성한다
                var markerImage = new kakao.maps.MarkerImage(markerImageUrl, markerImageSize, markerImageOptions);

    // 지도 시점 변화 완료 이벤트를
    // 4. 등록한다 [  idle(드래그 완료시 이벤트발생 ) vs bounds_changed(드래그 중에 이벤트발생 )  ]
    kakao.maps.event.addListener(map, 'idle', function () {
            // 클러스터 초기화
            clusterer.clear();
            $.ajax({
                url: '/room/roomlist' ,
                data : JSON.stringify(  map.getBounds() ) , // 현재 보고 있는 지도 범위 [ 동서남북 좌표 ]
                method : 'POST' ,
                contentType : 'application/json' ,
                success : function( data ){
                        console.log(data); // 통신 확인
                        // 마커목록 생성
                          var markers = $(data.positions).map(function(i, position) {

                            // 마커 하나 생성  start
                            var marker =  new kakao.maps.Marker({
                                position : new kakao.maps.LatLng( position.lat, position.lng) ,
                                image : markerImage // 마커의 이미지
                            });

                                 // 마커에 클릭 이벤트를 등록한다 (우클릭 : rightclick)
                                    kakao.maps.event.addListener(marker, 'click', function() {
                                        alert(" 룸 이름 : " + position.rname );
                                    });

                               return marker;

                             //  마커 하나 생성 end

                        }); // markers end

                         // 클러스터에 마커 추가
                        clusterer.addMarkers(markers);
                } // sueess end
            }); // ajax end
    }); // 이벤트 end


    // 5.
    // 마커 클러스터러에 클릭이벤트를 등록합니다
    // 마커 클러스터러를 생성할 때 disableClickZoom을 true로 설정하지 않은 경우
    // 이벤트 헨들러로 cluster 객체가 넘어오지 않을 수도 있습니다
    kakao.maps.event.addListener(clusterer, 'clusterclick', function(cluster) {
        // 현재 지도 레벨에서 1레벨 확대한 레벨
        var level = map.getLevel()-1;
        // 지도를 클릭된 클러스터의 마커의 위치를 기준으로 확대합니다
        map.setLevel(level, {anchor: cluster.getCenter()});
    });

}); // 현재 내 위치의 위도경도 구하기 end