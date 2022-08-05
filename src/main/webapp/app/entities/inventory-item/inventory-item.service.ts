import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInventoryItem } from 'app/shared/model/inventory-item.model';

type EntityResponseType = HttpResponse<IInventoryItem>;
type EntityArrayResponseType = HttpResponse<IInventoryItem[]>;

@Injectable({ providedIn: 'root' })
export class InventoryItemService {
  public resourceUrl = SERVER_API_URL + 'api/inventory-items';

  constructor(protected http: HttpClient) {}

  create(inventoryItem: IInventoryItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryItem);
    return this.http
      .post<IInventoryItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(inventoryItem: IInventoryItem): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inventoryItem);
    return this.http
      .put<IInventoryItem>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IInventoryItem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInventoryItem[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(inventoryItem: IInventoryItem): IInventoryItem {
    const copy: IInventoryItem = Object.assign({}, inventoryItem, {
      receivedDate: inventoryItem.receivedDate && inventoryItem.receivedDate.isValid() ? inventoryItem.receivedDate.toJSON() : undefined,
      manufacturedDate:
        inventoryItem.manufacturedDate && inventoryItem.manufacturedDate.isValid() ? inventoryItem.manufacturedDate.toJSON() : undefined,
      expiryDate: inventoryItem.expiryDate && inventoryItem.expiryDate.isValid() ? inventoryItem.expiryDate.toJSON() : undefined,
      retestDate: inventoryItem.retestDate && inventoryItem.retestDate.isValid() ? inventoryItem.retestDate.toJSON() : undefined,
      activationValidTrue:
        inventoryItem.activationValidTrue && inventoryItem.activationValidTrue.isValid()
          ? inventoryItem.activationValidTrue.toJSON()
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.receivedDate = res.body.receivedDate ? moment(res.body.receivedDate) : undefined;
      res.body.manufacturedDate = res.body.manufacturedDate ? moment(res.body.manufacturedDate) : undefined;
      res.body.expiryDate = res.body.expiryDate ? moment(res.body.expiryDate) : undefined;
      res.body.retestDate = res.body.retestDate ? moment(res.body.retestDate) : undefined;
      res.body.activationValidTrue = res.body.activationValidTrue ? moment(res.body.activationValidTrue) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((inventoryItem: IInventoryItem) => {
        inventoryItem.receivedDate = inventoryItem.receivedDate ? moment(inventoryItem.receivedDate) : undefined;
        inventoryItem.manufacturedDate = inventoryItem.manufacturedDate ? moment(inventoryItem.manufacturedDate) : undefined;
        inventoryItem.expiryDate = inventoryItem.expiryDate ? moment(inventoryItem.expiryDate) : undefined;
        inventoryItem.retestDate = inventoryItem.retestDate ? moment(inventoryItem.retestDate) : undefined;
        inventoryItem.activationValidTrue = inventoryItem.activationValidTrue ? moment(inventoryItem.activationValidTrue) : undefined;
      });
    }
    return res;
  }
}
