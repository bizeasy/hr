import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { ItemIssuanceDetailComponent } from 'app/entities/item-issuance/item-issuance-detail.component';
import { ItemIssuance } from 'app/shared/model/item-issuance.model';

describe('Component Tests', () => {
  describe('ItemIssuance Management Detail Component', () => {
    let comp: ItemIssuanceDetailComponent;
    let fixture: ComponentFixture<ItemIssuanceDetailComponent>;
    const route = ({ data: of({ itemIssuance: new ItemIssuance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [ItemIssuanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ItemIssuanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ItemIssuanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load itemIssuance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.itemIssuance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
