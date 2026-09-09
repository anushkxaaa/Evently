package com.evently.common.cdc;

import com.evently.common.dto.CdcEventPayload;



public class CdcMessageProcessor {

    private CdcMessageProcessor() {}

    public static <ID, T extends CdcRecord> void process(CdcEventPayload<T> payload, CdcChangeHandler<ID, T> handler) {
        if (payload.isSnapshot() || "c".equals(payload.getOp()) || "u".equals(payload.getOp())) {
            T row = payload.getAfter();
            ID id = handler.extractId(row);
            try {
                handler.upsert(row);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (payload.isDelete()) {
            T row = payload.getBefore();
            ID id = handler.extractId(row);
            try {
                handler.delete(id);

            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else {
           System.out.print(payload.getOp());
        }
    }
}