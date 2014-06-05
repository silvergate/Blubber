package com.dcrux.blubber.riak;

import com.dcrux.blubber.abstr.implementables.*;
import com.dcrux.blubber.abstr.implementables.keyvalue.*;
import com.dcrux.blubber.abstr.implementation.*;
import com.dcrux.blubber.abstr.implementation.keyvalue.MemoryKeyValue;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;

/**
 * Created by caelis on 26/05/14.
 */
public class DemoModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(IBinaryValueSvc.class).to(BinaryValueSvc.class).in(Singleton.class);
        MemoryKeyValue memoryKeyValue = new MemoryKeyValue();
        binder.bind(IKvCreateValue.class).toInstance(memoryKeyValue);
        binder.bind(IKvGetValue.class).toInstance(memoryKeyValue);
        binder.bind(IKvIndexValue.class).toInstance(memoryKeyValue);
        binder.bind(IKvQueryIndex.class).toInstance(memoryKeyValue);
        binder.bind(IKvSetValue.class).toInstance(memoryKeyValue);
        binder.bind(IKvDeleteValue.class).toInstance(memoryKeyValue);
        binder.bind(IKvRemoveAllIndex.class).toInstance(memoryKeyValue);
        binder.bind(IKvRemoveIndex.class).toInstance(memoryKeyValue);

        binder.bind(IDefinitionStorageSvc.class).to(DefinitionStorageSvc.class).in(Singleton.class);
        binder.bind(IDefinitionHashGenSvc.class).to(DefinitionHashGenSvc.class).in(Singleton.class);
        binder.bind(IBinaryValueSvc.class).to(BinaryValueSvc.class).in(Singleton.class);
        binder.bind(ICheckedIncubationHeader.class).to(CheckedIncubationHeader.class).in(Singleton.class);
        binder.bind(ICommitMetaIndexSvc.class).to(CommitMetaIndexSvc.class).in(Singleton.class);
        binder.bind(ICommitSvc.class).to(CommitSvc.class).in(Singleton.class);
        binder.bind(IConnectionSvc.class).to(ConnectionSvc.class).in(Singleton.class);
        binder.bind(ICreateItemSvc.class).to(CreateItemSvc.class).in(Singleton.class);
        binder.bind(IDefaultsSvc.class).to(DefaultsSvc.class).in(Singleton.class);
        binder.bind(IDefinitionByIdSvc.class).to(DefinitionByIdSvc.class).in(Singleton.class);
        binder.bind(IFieldKeyGenSvc.class).to(FieldKeyGenSvc.class).in(Singleton.class);
        binder.bind(IGetSpecialFieldValueSvc.class).to(GetSpecialFieldValueSvc.class).in(Singleton.class);
        binder.bind(IFieldValueGeneratorSvc.class).to(FieldValueGeneratorSvc.class).in(Singleton.class);
        binder.bind(IFieldValueObjectGeneratorSvc.class).to(FieldValueObjectGeneratorSvc.class).in(Singleton.class);
        binder.bind(IGetFieldDefSvc.class).to(GetFieldDefSvc.class).in(Singleton.class);
        binder.bind(IGetFieldValueSvc.class).to(GetFieldValueSvc.class).in(Singleton.class);
        binder.bind(IGetLiveHeaderSvc.class).to(GetLiveHeaderSvc.class).in(Singleton.class);
        binder.bind(IIncubationHeaderSvc.class).to(IncubationHeaderSvc.class).in(Singleton.class);
        binder.bind(IItemCleanup.class).to(ItemCleanupSvc.class).in(Singleton.class);
        binder.bind(IItemDefIdFromHashSvc.class).to(ItemDefIdFromHashSvc.class).in(Singleton.class);
        binder.bind(INamesSvc.class).to(NamesSvc.class).in(Singleton.class);
        binder.bind(ISetFieldValueSvc.class).to(SetFieldValueSvc.class).in(Singleton.class);
        binder.bind(ITimeSvc.class).to(TimeSvc.class).in(Singleton.class);
        binder.bind(IUnleafeSvc.class).to(UnleafeSvc.class).in(Singleton.class);
        binder.bind(IUserSvc.class).to(UserSvc.class).in(Singleton.class);
        binder.bind(IValidateFieldValueSvc.class).to(ValidateFieldValueSvc.class).in(Singleton.class);

    }
}
