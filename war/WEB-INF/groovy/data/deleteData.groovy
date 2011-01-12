import groovyx.gaelyk.plugins.datastore.viewer.data.EntityBrowsingData

log.info "Deleting data"

def kind = params.kind
def ids = params.ids

if(ids) {
    def entityIds = []

    // Single ID
    if(ids instanceof String) {
        entityIds << ids
    }
    // Multiple IDs
    else {
        entityIds = ids
    }
    
    entityIds.each { entityId ->
        datastoreViewerService.deleteEntity(kind, entityId.toLong())
    }
}

def offset = params.offset?.toInteger()
def limit = params.limit?.toInteger()

EntityBrowsingData entityBrowsingData = datastoreViewerService.getEntityBrowsingData(kind, offset, limit)
request.kinds = entityBrowsingData.kinds
request.kind = entityBrowsingData.selectedKind
request.kindProperties = entityBrowsingData.kindProperties
request.page = entityBrowsingData.entityPage

forward '/WEB-INF/pages/data/browse.gtpl'