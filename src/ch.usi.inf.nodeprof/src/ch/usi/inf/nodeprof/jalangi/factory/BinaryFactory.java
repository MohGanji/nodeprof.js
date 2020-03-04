/*******************************************************************************
 * Copyright 2018 Dynamic Analysis Group, Università della Svizzera Italiana (USI)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package ch.usi.inf.nodeprof.jalangi.factory;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.EventContext;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObject;

import ch.usi.inf.nodeprof.handlers.BaseEventHandlerNode;
import ch.usi.inf.nodeprof.handlers.BinaryEventHandler;

public class BinaryFactory extends AbstractFactory {

    public BinaryFactory(Object jalangiAnalysis, DynamicObject pre,
                    DynamicObject post) {
        super("binary", jalangiAnalysis, pre, post);
    }

    @Override
    public BaseEventHandlerNode create(EventContext context) {
        return new BinaryEventHandler(context) {
            @Node.Child private InteropLibrary preDispatch = (pre == null) ? null : createDispatchNode();
            @Node.Child private InteropLibrary postDispatch = (post == null) ? null : createDispatchNode();

            @Override
            public void executePre(VirtualFrame frame, Object[] inputs) throws InteropException {
                if (pre != null && !isLogic()) {
                    wrappedDispatchExecution(preDispatch, pre,
                                    getSourceIID(), getOp(), getLeft(inputs), getRight(inputs));
                }
            }

            @Override
            public void executePost(VirtualFrame frame, Object result,
                            Object[] inputs) throws InteropException {
                if (post != null && !isLogic()) {
                    wrappedDispatchExecution(postDispatch, post,
                                    getSourceIID(), getOp(), getLeft(inputs), getRight(inputs), convertResult(result));
                }
            }
        };
    }
}
