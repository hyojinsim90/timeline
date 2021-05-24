import React, { useState, useEffect } from 'react'
import { Menu, Button } from 'antd'
import styled from 'styled-components'
import { BrowserView, MobileView } from 'react-device-detect'
import { MenuOutlined, MenuFoldOutlined } from '@ant-design/icons'
import { Link } from 'react-router-dom'
import { useSelector } from 'react-redux'
import { useCookies } from "react-cookie"
import Axios from "axios"

const Logo =  styled.div`
  font-weight: bold;
  font-size: 3.5vh;
  padding: 1rem;
  span {
    color: #f0f0f0;
  }
  a {
    color: black !important;
  }
  + div {
    border-bottom: 1px solid #f0f0f0;
  }
`;

const MenuList = styled.div`
  display: flex;
  justify-content: space-between;
`;

const LogoM = styled.div`
  font-weight: bold;
  font-size: 3.5vh;
  padding: 1rem;
  display: flex;
  justify-content: space-between;
  span {
    color: #f0f0f0;
  }
  a {
    color: black !important;
  }

  Button {
    background: black !important;
    border: none;
  }
`;

const NavBar = () => {

  const [toggleMenu, setToggleMenu] = useState(false)
  const [toggleBar, setToggleBar] = useState(true)
  const [cookies, setCookie, removeCookie] = useCookies([])
  const [auth, setAuth] = useState(false)

  useEffect(() => {
    if(cookies.tl_token !== undefined) {
      setAuth(true)
    }
  }, [cookies])

  const toggleChange = () => {
    setToggleMenu(!toggleMenu)
    setToggleBar(!toggleBar)
  }

  const onMenuClick = () => {
    setToggleMenu(!toggleMenu)
    setToggleBar(!toggleBar)
  }

  const onLogout = () => {
    if(auth && cookies.tl_e) {
      Axios.get(`/auth/logout/${cookies.tl_e}`)
        .then(res => {
          removeCookie("tl_e")
          removeCookie("tl_re")
          removeCookie("tl_exp")
          removeCookie("tl_token")
          window.location.reload()
        })
    }
  }

  return(
    <div>
      <BrowserView>
        <Logo>
          <Link to="/">
            TI<span>MELI</span>NE
          </Link>
        </Logo>
        <MenuList>
          <Menu selectedKeys="mail" mode="horizontal">
            <Menu.Item key="timeline">
              타임라인
            </Menu.Item>
            <Menu.Item key="class">
              클래스
            </Menu.Item>
          </Menu>
          <Menu mode="horizontal">
            <Menu.Item key="mypage">
              <Link to="/mypage">
                마이페이지
              </Link>
            </Menu.Item>
            { auth ?
            <>
              <Menu.Item key="logout" onClick={onLogout}>
                로그아웃
              </Menu.Item>
            </>
            :
            <>
              <Menu.Item key="login">
                <Link to="/login">
                  로그인
                </Link>
              </Menu.Item>
              <Menu.Item key="signup">
                <Link to="/signup">
                  회원가입
                </Link>
              </Menu.Item>
            </>
            }
          </Menu>
        </MenuList>
      </BrowserView>
      <MobileView>
        <LogoM>
          <Link to="/">
            TI<span>MELI</span>NE
          </Link>
          <div >
            <Button type="primary" onClick={toggleChange} style={{ marginBottom: 16 }}>
              { toggleBar ? <MenuOutlined /> : <MenuFoldOutlined /> }
            </Button>
          </div>
        </LogoM>
        { toggleMenu ?
          <Menu
            defaultSelectedKeys={['1']}
            mode="inline"
            theme="light"
            inlineCollapsed={toggleBar}
            onClick={onMenuClick}
          >
            <Menu.Item key="timeline">
              타임라인
            </Menu.Item>
            <Menu.Item key="class">
              클래스
            </Menu.Item>
            <Menu.Item key="mypage">
              <Link to="/mypage">
                마이페이지
              </Link>
            </Menu.Item>
            { auth ?
              <>
                <Menu.Item key="logout" onClick={onLogout}>
                  로그아웃
                </Menu.Item>
              </>
              :
              <>
                <Menu.Item key="login">
                  <Link to="/login">
                    로그인
                  </Link>
                </Menu.Item>
                <Menu.Item key="signup">
                  <Link to="/signup">
                    회원가입
                  </Link>
                </Menu.Item>
              </>
            }
          </Menu>
          : <></>
        }
      </MobileView>
    </div>
  )
}

export default NavBar;
