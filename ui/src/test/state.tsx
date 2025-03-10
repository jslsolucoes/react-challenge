import {Provider} from "react-redux";
import {store} from "../state/store/store";

export function withProvider(children: React.ReactNode) {
    return (
        <Provider store={store}>
            {children}
        </Provider>
    )
}