import React, { useState, useEffect } from "react"
import { BrowserView, MobileView } from "react-device-detect"
import { useSelector } from "react-redux"
import { useCookies } from "react-cookie"
import Axios from "axios"
import WebNavBar from "./Sections/WebNavBar"
import MobileNavBar from "./Sections/MobileNavBar"
import { useHistory } from "react-router-dom"

const NavBar = (props) => {
  const [cookies, setCookie, removeCookie] = useCookies([])
  const [auth, setAuth] = useState(false)

  const history = useHistory()

  useEffect(() => {
    if(cookies.tl_token !== undefined) {
      setAuth(true)
    }
  }, [cookies])

  const onLogout = () => {
    if(auth && cookies.tl_e) {
      Axios.get(`/auth/logout/${cookies.tl_e}`)
        .then(res => {
          removeCookie("tl_e")
          removeCookie("tl_re")
          removeCookie("tl_exp")
          removeCookie("tl_token")
          setAuth(false)
          window.location.reload()
          history.push("/login")
        })
        .catch(err => {
          removeCookie("tl_e")
          removeCookie("tl_re")
          removeCookie("tl_exp")
          removeCookie("tl_token")
          setAuth(false)
          window.location.reload()
          history.push("/")
        })
    }
  }

  return(
    <div>
      <BrowserView>
        <WebNavBar auth={auth} onLogout={onLogout} />
      </BrowserView>
      <MobileView>
        <MobileNavBar auth={auth} onLogout={onLogout} />
      </MobileView>
    </div>
  )
}

export default NavBar;
