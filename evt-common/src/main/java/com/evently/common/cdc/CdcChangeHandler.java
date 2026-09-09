package com.evently.common.cdc;

public interface CdcChangeHandler<ID,T extends CdcRecord>{
    ID extractId(T row);
    void upsert(T row);
    void delete(ID id);
}
