package com.evently.common.cdc;

import com.evently.common.dto.CdcEventPayload;

public class CdcMessageProcessor {

    private CdcMessageProcessor() {}

    public static <ID, T extends CdcRecord> void process(
            CdcEventPayload<T> payload,
            CdcChangeHandler<ID, T> handler) {

        System.out.println("CDC OP = " + payload.getOp());
        System.out.println("CDC SNAPSHOT = " + payload.isSnapshot());

        if (payload.isSnapshot()
                || "c".equals(payload.getOp())
                || "u".equals(payload.getOp())) {

            T row = payload.getAfter();

            System.out.println("AFTER ROW = " + row);

            ID id = handler.extractId(row);

            System.out.println("CALLING UPSERT FOR ID = " + id);

            try {
                handler.upsert(row);

                System.out.println("UPSERT SUCCESS FOR ID = " + id);

            } catch (Exception ex) {

                System.out.println("UPSERT FAILED FOR ID = " + id);
                ex.printStackTrace();
            }

        } else if (payload.isDelete()) {

            T row = payload.getBefore();

            ID id = handler.extractId(row);

            System.out.println("CALLING DELETE FOR ID = " + id);

            try {
                handler.delete(id);

                System.out.println("DELETE SUCCESS FOR ID = " + id);

            } catch (Exception ex) {

                System.out.println("DELETE FAILED FOR ID = " + id);
                ex.printStackTrace();
            }

        } else {

            System.out.println("IGNORED CDC OP = " + payload.getOp());
        }
    }
}