import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PartyClassificationDetailComponent } from 'app/entities/party-classification/party-classification-detail.component';
import { PartyClassification } from 'app/shared/model/party-classification.model';

describe('Component Tests', () => {
  describe('PartyClassification Management Detail Component', () => {
    let comp: PartyClassificationDetailComponent;
    let fixture: ComponentFixture<PartyClassificationDetailComponent>;
    const route = ({ data: of({ partyClassification: new PartyClassification(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PartyClassificationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PartyClassificationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartyClassificationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load partyClassification on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.partyClassification).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
