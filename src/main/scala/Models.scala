
type Name = String

/* data classes */
case class Photo(string: String)
case class User(login: Name, password: String, photos: List[Photo])
case class Session(id: Int)

/* server data */
val users = Map[Name, User]()
val sessions = Map[Name, Session]

/* reposnses */
case class LoginResponse(success: Boolean, login: String, sessionKey: Int)
case class RegisterResponse(success: Boolean)

/* contracts*/
def register(login: String, password: String): RegisterResponse = ???
def login(login: String, password: String): LoginResponse = ???


