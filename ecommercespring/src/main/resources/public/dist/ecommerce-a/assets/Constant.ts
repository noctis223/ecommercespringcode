// addresses
export const ADDRESS_STORE_SERVER = "http://localhost:8082";
export const ADDRESS_AUTHENTICATION_SERVER = "http://localhost:8180";
export const ADDRESS_CLIENT="http://localhost:4200"

// authentication
export const REALM = "Utente";
export const CLIENT_ID = "Ecommerce";
export const CLIENT_SECRET = "***";
export const REQUEST_LOGIN = "/auth/realms/" + REALM + "/protocol/openid-connect/token";
export const REQUEST_LOGOUT = "/auth/realms/" + REALM + "/protocol/openid-connect/logout";

// requests
export const REQUEST_ADD_USER = "/utenti/registrazioneNuovoUtente";

//USER
export const REQUEST_VIEW_USER = "/utenti/alluser";
export const REQUEST_VIEW_OLD = "/utenti/olduser";
export const REQUEST_SEARCH_USEREMAIL = "/utenti/search/email";
export const REQUEST_SEARCH_USERECOGNOME = "/utenti/search/by_cogname";

//CARRELLO
export const REQUEST_CARRELLO_VIEW = "/carrello";
export const REQUEST_CARRELLO_ADD = "/carrello/add/{pid}/{qty}";
export const REQUEST_CARRELLO_UPDATE = "/carrello/update/{pid}/{qty}";
export const REQUEST_CARRELLO_DELEDE = "/carrello/delede/{pid}/{qty}";

//ACCESSO
export const REQUEST_HOME_USER = "/home/user";
export const REQUEST_HOME_ADMIN = "/home/admin";

//ORDINE
export const REQUEST_ORDINI_NEW = "/ordini/ordine";
export const REQUEST_ORDINI_ALL = "/ordini/mieiordini";
export const REQUEST_ORDINI_SEARCH = "/ordini/mieiordini/{id}";
export const REQUEST_ORDINI_DETAILS = "/ordini/mieiordini/{id}/dettagliordine";
export const REQUEST_ORDINI_SEARCHDATA = "/ordini/mieiordini/{stardate}/{enddate}";

//PRODOTTO
export const REQUEST_PRODOTTO_NEW = "/prodotti/aggiungiprodotto";
export const REQUEST_PRODOTTO_ADDQUANTITY = "/prodotti/aggiungiquantita";
export const REQUEST_PRODOTTO_SHOW = "/prodotti/showall";
export const REQUEST_PRODOTTO_SEARCHNAME = "/prodotti/search/by_name";
export const REQUEST_PRODOTTO_SEARCHBRCODE = "/prodotti/search/by_Brcode";
export const REQUEST_PRODOTTO_SEARCHTIPOLOGIA = "/prodotti/search/Tipologia";
export const REQUEST_PRODOTTO_SEARCHADVANCE = "/prodotti/search/advance";

//RECLAMI
export const REQUEST_RECLAMO_NEW = "/ordini/{ordine}/creareclamo";

//TIPOLOGIA
export const REQUEST_TIPOLOGIA_NEW = "/tipologie/creanuovatipologia";
export const REQUEST_TIPOLOGIA_VIEW = "/tipologie/alltipo";

//NON SERVONO
export const LINK_FIRST_SETUP_PASSWORD = "https://" + ADDRESS_AUTHENTICATION_SERVER + "/auth/realms/" + REALM + "/protocol/openid-connect/auth?response_type=code&client_id=" + CLIENT_ID + "&redirect_uri=https://" + ADDRESS_CLIENT;
export const LINK_RESET_PASSWORD = "https://" + ADDRESS_AUTHENTICATION_SERVER + "/auth/realms/" + REALM + "/login-actions/reset-credentials?client_id=account";

