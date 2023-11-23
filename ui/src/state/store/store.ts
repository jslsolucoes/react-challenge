import {Action, configureStore, ThunkAction} from '@reduxjs/toolkit';
import {highlightSlice} from "../../pages/BreakifyPage/highlighSlice";

export const store = configureStore({
    reducer: {
        highlights: highlightSlice.reducer,
    },
});

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;
export type AppThunk<ReturnType = void> = ThunkAction<
    ReturnType,
    RootState,
    unknown,
    Action<string>
>;