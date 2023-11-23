import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {AppThunk, RootState} from "../../state/store/store";
import api from "./api";


export type HighlightWord = {
    word: string;
    highlights: {
        substring: string;
        startIndex: number;
        endIndex: number;
        periodicElementSymbol: string;
        periodicElementAtomicNumber: number;
    }[];
}

export type Highlight = {
    firstName: HighlightWord,
    lastName: HighlightWord,
}

export type HighlightState = {
    highlight: Highlight;
}

const initialState: HighlightState = {
    highlight: {
        firstName: {
            word: '',
            highlights: [],
        },
        lastName: {
            word: '',
            highlights: [],
        },
    },
};

export type CreateNewHighlightParams = {
    firstName: string;
    lastName: string;
}

export const createNewHighlight = createAsyncThunk(
    'highlighter/createNewHighlight',
    async (params: CreateNewHighlightParams) => {
        return await api.createNewHighlight(params.firstName, params.lastName);
    }
);

export const highlightSlice = createSlice({
    name: 'highlighter',
    initialState,
    reducers: {
        clear: (state) => {
            state.highlight = {
                firstName: {
                    word: '',
                    highlights: [],
                },
                lastName: {
                    word: '',
                    highlights: [],
                },
            };
        },
    },
    extraReducers(builder) {
        builder.addCase(createNewHighlight.fulfilled, (state, action) => {
            return {highlight: action.payload}
        })
    }
});


export const debugHighlight = (): AppThunk => (dispatch, getState) => {
    const state = getState();
    console.log(state.highlights.highlight);
};

export const selectHighlight = (state: RootState) => state.highlights.highlight;

export const {clear} = highlightSlice.actions;
