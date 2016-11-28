
type Name = String

/* data classes */
case class Photo(string: String)
case class User(login: Name, password: String, photos: List[Photo])
case class Session(id: Int)

/* server data */
val users = Map[Name, User]()
val sessions = Map[Name, Session]()

/* reposnses */
case class LoginResponse(success: Boolean, login: Name = "", sessionKey: Session = Session(-1))
case class RegisterResponse(success: Boolean)

/* contracts*/
def register(login: Name, password: String): RegisterResponse = {
  if (users.contains(login)) {
    return RegisterResponse(false)
  }

  users(login) = User(login, password, List())
  RegisterResponse(true)
}

def login(login: Name, password: String): LoginResponse = {
  if (!users.contains(login)) {
    return LoginResponse(false)
  }

  val id = sessions.size
  sessions(login) = Session(id)

  LoginResponse(true, login, Session(id))
}
