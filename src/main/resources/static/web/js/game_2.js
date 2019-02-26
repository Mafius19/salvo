

$(function () {
  loadData();
});

function getParameterByName(name) {
  var match = RegExp('[?&]' + name + '=([^&]*)').exec(window.location.search);
  return match && decodeURIComponent(match[1].replace(/\+/g, ' '));
}

function loadData() {
  $.get('/api/game_view/' + getParameterByName('gp'))
    .done(function (data) {
      var playerInfo;
      if (data.gamePlayer[0].id == getParameterByName('gp'))
        playerInfo = [data.gamePlayer[0].player, data.gamePlayer[1].player];
      else
        playerInfo = [data.gamePlayer[1].player, data.gamePlayer[0].player];

      $('#playerInfo').text(playerInfo[0].userName + '(you) vs ' + playerInfo[1].userName);

        data.ships.forEach(function (shipPiece) {
          shipPiece.locations.forEach(function (shipLocation) {
            var hited = isHit(shipLocation,data.salvoes,playerInfo[0].id);
          if(hited > 0){
            $('#B_' + shipLocation).addClass('ship-piece-hited')
            $('#B_' + shipLocation).text(hited);}
          else
            $('#B_' + shipLocation).addClass('ship-piece');
        });
      });
      data.salvoes.forEach(function (salvo) {
        if (playerInfo[0].id === salvo.player) {
          salvo.locations.forEach(function (location) {
            $('#S_' + location).addClass('salvo');
          });
        } else {
          salvo.locations.forEach(function (location) {
            $('#_' + location).addClass('salvo');
          });
        }
      });
    })
    .fail(function (jqXHR, textStatus) {
      alert('Failed: ' + textStatus);
    });
}

function isHit(shipLocation,salvoes,playerId) {
  var hit = 0;
  salvoes.forEach(function (salvo) {
    if(salvo.player != playerId)
      salvo.locations.forEach(function (location) {
        if(shipLocation === location)
          hit = salvo.turn;
      });
  });
  return hit;
}