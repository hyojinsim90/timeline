import React, { Suspense } from "react"
import { Switch, Route } from "react-router-dom"
import "./App.css"
import NavBar from "./components/NavBar/NavBar"
import LoginPage from "./components/AuthPage/LoginPage"
import SignupPage from "./components/AuthPage/SignupPage"
import FindPwPage from "./components/AuthPage/FindPwPage"
import MyPage from "./components/MyPage/MyPage"
import MyTimeline from "./components/MyPage/MyTimeline"
import CreateTimeline from "./components/CreateTimeline/CreateTimeline"
import TimelineList from "./components/ManageTimeline/TimelineList"
import TimelineItem from "./components/ManageTimeline/TimelineItem"
import MyInfo from "./components/MyPage/MyInfo"
import Main from "./components/Main/Main"
import Detail from "./components/Detail/Detail"
import Auth from "./hoc/auth"

function App() {
  return (
    <Suspense fallback={(<div>...</div>)}>
      <NavBar />
      <div className="App">
        <Switch>
          <Route exact path="/" component={Main}></Route>
          <Route exact path="/:timelineId" component={Detail}></Route>
          <Route exact path="/login" component={Auth(LoginPage, true)}></Route>
          <Route exact path="/signup" component={SignupPage}></Route>
          <Route exact path="/findpw" component={FindPwPage}></Route>
          <Route exact path="/mypage" component={Auth(MyPage, true)}></Route>
          <Route exact path="/mytimeline" component={Auth(MyTimeline, true)}></Route>
          <Route exact path="/myinfo" component={Auth(MyInfo, true)}></Route>
          <Route exact path="/createtimeline" component={Auth(CreateTimeline, true)}></Route>
          <Route exact path="/timelinelist" component={Auth(TimelineList, true)}></Route>
          <Route exact path="/timelinelist/:timelineId" component={Auth(TimelineItem, true)}></Route>
        </Switch>
      </div>
    </Suspense>
  )
}

export default App
