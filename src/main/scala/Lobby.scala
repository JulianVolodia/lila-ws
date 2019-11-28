package lila.ws

import scala.concurrent.duration._

import ipc.ClientIn.LobbyPong
import ipc.LilaIn

final class Lobby(
    lila: Lila,
    groupedWithin: util.GroupedWithin
) {

  private val lilaIn = lila.emit.lobby

  val connect = groupedWithin[(Sri, Option[User.ID])](6, 479.millis) { connects =>
    lilaIn(LilaIn.ConnectSris(connects))
  }

  val disconnect = groupedWithin[Sri](50, 487.millis) { sris =>
    lilaIn(LilaIn.DisconnectSris(sris))
  }

  object pong {

    private var value = LobbyPong(0, 0)

    def get = value

    def update(members: Int, rounds: Int): Unit = {
      value = LobbyPong(members, rounds)
    }
  }
}
