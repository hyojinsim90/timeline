import React, { Suspense } from 'react';
import { Switch, Route } from 'react-router-dom';
import './App.css';
import NavBar from './components/NavBar/NavBar';
import LoginPage from './components/AuthPage/LoginPage';
import SignupPage from './components/AuthPage/SignupPage';
import MyPage from './components/MyPage/MyPage'
import MyTimeline from './components/MyPage/MyTimeline';

function App() {
  return (
    <Suspense fallback={(<div>...</div>)}>
      <NavBar />
      <div className="App">
        <Switch>
          <Route exact path="/login" component={LoginPage}></Route>
          <Route exact path="/signup" component={SignupPage}></Route>
          <Route exact path="/mypage" component={MyPage}></Route>
          <Route exact path="/mytimeline" component={MyTimeline}></Route>
        </Switch>
      </div>
    </Suspense>
  );
}

export default App;
