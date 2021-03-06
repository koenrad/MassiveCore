package com.massivecraft.massivecore.store;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.plugin.Plugin;

import com.massivecraft.massivecore.Predictate;
import com.massivecraft.massivecore.xlib.gson.JsonElement;

public interface CollInterface<E>
{
	// -------------------------------------------- //
	// WHAT DO WE HANDLE?
	// -------------------------------------------- //
	public String getName();
	public String getBasename();
	public String getUniverse();
	public Class<E> getEntityClass();
	
	// -------------------------------------------- //
	// SUPPORTING SYSTEM
	// -------------------------------------------- //
	public Plugin getPlugin();
	
	public Db getDb();
	public Object getCollDriverObject();
	
	// -------------------------------------------- //
	// STORAGE
	// -------------------------------------------- //
	public Map<String, E> getId2entity();
	public E get(Object oid);
	public E get(Object oid, boolean creative);
	public Collection<String> getIds();
	public Collection<String> getIdsRemote();
	public boolean containsId(Object oid);
	
	public Map<E, String> getEntity2id();
	public String getId(Object entity);
	public boolean containsEntity(Object entity);
	public Collection<E> getAll();
	public List<E> getAll(Predictate<? super E> where);
	public List<E> getAll(Predictate<? super E> where, Comparator<? super E> orderby);
	public List<E> getAll(Predictate<? super E> where, Comparator<? super E> orderby, Integer limit);
	public List<E> getAll(Predictate<? super E> where, Comparator<? super E> orderby, Integer limit, Integer offset);
	
	public String fixId(Object oid);
	
	// -------------------------------------------- //
	// BEHAVIOR
	// -------------------------------------------- //
	
	public boolean isCreative();
	public void setCreative(boolean creative);
	
	public boolean isLowercasing();
	public void setLowercasing(boolean lowercasing);
	
	// A default entity will not be saved.
	// This is often used together with creative collections to save disc space.
	public boolean isDefault(E entity);
	
	// -------------------------------------------- //
	// COPY AND CREATE
	// -------------------------------------------- //
	
	public void copy(Object fromo, Object too);
	
	// This simply creates and returns a new instance
	// It does not detach/attach or anything. Just creates a new instance.
	public E createNewInstance();
	
	// Create new instance with automatic id
	public E create();
	// Create new instance with the requested id
	public E create(Object oid);
	
	// -------------------------------------------- //
	// ATTACH AND DETACH
	// -------------------------------------------- //
	
	public String attach(E entity);
	public String attach(E entity, Object oid);
	
	public E detachEntity(Object entity);
	public E detachId(Object oid);
	
	public void preAttach(E entity, String id);
	public void postAttach(E entity, String id);
	
	public void preDetach(E entity, String id);
	public void postDetach(E entity, String id);
	
	// -------------------------------------------- //
	// IDENTIFIED CHANGES
	// -------------------------------------------- //
	
	/*
	public Set<L> localAttachIds();
	public Set<L> localDetachIds();
	public Set<L> changedIds();
	public void clearIdentifiedChanges(Object oid);
	*/
	
	// -------------------------------------------- //
	// SYNC LOG
	// -------------------------------------------- //
	
	/*
	public Map<L, Long> lastMtime();
	public Map<L, Object> lastRaw();
	public Set<L> lastDefault();
	public void clearSynclog(Object oid);
	*/
	
	public Map<String, Long> getSyncMap(boolean in);
	public long getSyncCount(String name, boolean in);
	public void addSyncCount(String name, boolean in);
	
	// -------------------------------------------- //
	// SYNC LOWLEVEL IO ACTIONS
	// -------------------------------------------- //
	
	public E removeAtLocal(Object oid);
	public void removeAtRemote(Object oid);
	public void saveToRemote(Object oid);
	public void loadFromRemote(Object oid, Entry<JsonElement, Long> remoteEntry);
	
	// -------------------------------------------- //
	// SYNC EXAMINE AND DO
	// -------------------------------------------- //
	
	public Modification examineId(Object oid);
	public Modification examineId(Object oid, Long remoteMtime);
	
	public Modification syncId(Object oid);
	public Modification syncId(Object oid, Modification modificationState);
	public Modification syncId(Object oid, Modification modificationState, Entry<JsonElement, Long> remoteEntry);
	public void syncIdentified(boolean safe);
	public void syncAll();
	public void identifyModifications();
	public void initLoadAllFromRemote();
	
	// -------------------------------------------- //
	// SYNC RUNNABLES / SCHEDULING
	// -------------------------------------------- //
	
	// The tickTask simply runs the onTick method.
	public Runnable getTickTask();
	public void onTick();
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public void init();
	public void deinit();
	public boolean inited();
	
	
}