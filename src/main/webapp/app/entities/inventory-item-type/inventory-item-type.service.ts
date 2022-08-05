import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInventoryItemType } from 'app/shared/model/inventory-item-type.model';

type EntityResponseType = HttpResponse<IInventoryItemType>;
type EntityArrayResponseType = HttpResponse<IInventoryItemType[]>;

@Injectable({ providedIn: 'root' })
export class InventoryItemTypeService {
  public resourceUrl = SERVER_API_URL + 'api/inventory-item-types';

  constructor(protected http: HttpClient) {}

  create(inventoryItemType: IInventoryItemType): Observable<EntityResponseType> {
    return this.http.post<IInventoryItemType>(this.resourceUrl, inventoryItemType, { observe: 'response' });
  }

  update(inventoryItemType: IInventoryItemType): Observable<EntityResponseType> {
    return this.http.put<IInventoryItemType>(this.resourceUrl, inventoryItemType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInventoryItemType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInventoryItemType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
