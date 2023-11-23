import React, {lazy, Suspense} from 'react';
import {Route, Routes} from "react-router";

const BreakifyPage = lazy(() => import('./pages/BreakifyPage'));

const App: React.FC = () => {
    return (
        <Suspense fallback={<div>Loading...</div>}>
            <Routes>
                <Route path="/" element={<BreakifyPage/>}/>
            </Routes>
        </Suspense>
    );
};

export default App;
